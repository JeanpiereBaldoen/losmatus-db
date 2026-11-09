package com.losmatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/** Verifies the Java mappings against the real, unmodified SQL delivered with the project. */
class SchemaCoverageTest {

    private static final Pattern SQL_TABLE = Pattern.compile(
            "CREATE TABLE IF NOT EXISTS `([^`]+)` \\(\\R(.*?)\\R\\) ENGINE", Pattern.DOTALL);
    private static final Pattern SQL_COLUMN = Pattern.compile("(?m)^  `([^`]+)` ");
    private static final Pattern SQL_FK = Pattern.compile("FOREIGN KEY \\(`([^`]+)`\\) REFERENCES");
    private static final Pattern SQL_INSERT = Pattern.compile("(?m)^INSERT INTO `([^`]+)` ");

    @Test
    void allTablesColumnsAndForeignKeysAreMapped() throws Exception {
        String ddl = Files.readString(Path.of("..", "database", "BD_LOSMATUS.sql"));
        Map<String, Set<String>> sqlColumns = new HashMap<>();
        Map<String, Set<String>> sqlFks = new HashMap<>();
        Matcher tables = SQL_TABLE.matcher(ddl);
        while (tables.find()) {
            String name = tables.group(1);
            sqlColumns.put(name, matches(SQL_COLUMN, tables.group(2)));
            sqlFks.put(name, matches(SQL_FK, tables.group(2)));
        }

        Map<String, Set<String>> javaColumns = new HashMap<>();
        Map<String, Set<String>> javaFks = new HashMap<>();
        Path entityPath = Path.of("src", "main", "java", "com", "losmatus", "entity");
        try (Stream<Path> sources = Files.list(entityPath)) {
            for (Path file : sources.filter(p -> p.toString().endsWith(".java")).toList()) {
                String className = file.getFileName().toString().replace(".java", "");
                Class<?> entityType = Class.forName("com.losmatus.entity." + className);
                assertNotNull(entityType.getAnnotation(Entity.class), className + " must be @Entity");
                Table table = entityType.getAnnotation(Table.class);
                assertNotNull(table, className + " must declare @Table");
                Set<String> columns = new HashSet<>();
                Set<String> fks = new HashSet<>();
                for (Field field : entityType.getDeclaredFields()) {
                    Column column = field.getAnnotation(Column.class);
                    if (column != null) {
                        columns.add(column.name());
                    }
                    JoinColumn join = field.getAnnotation(JoinColumn.class);
                    if (join != null) {
                        fks.add(join.name());
                    }
                }
                javaColumns.put(table.name(), columns);
                javaFks.put(table.name(), fks);
            }
        }

        assertEquals(47, sqlColumns.size(), "The expected dump contains 47 tables");
        assertEquals(sqlColumns, javaColumns, "Every SQL table and column must have a JPA mapping");
        assertEquals(sqlFks, javaFks, "Every SQL foreign key must have a JPA association");
        assertEquals(sqlColumns.keySet(), matches(SQL_INSERT, ddl),
                "Every table must have example records in the demonstration SQL");
    }

    private static Set<String> matches(Pattern pattern, String text) throws IOException {
        Set<String> values = new HashSet<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            values.add(matcher.group(1));
        }
        return values;
    }
}
