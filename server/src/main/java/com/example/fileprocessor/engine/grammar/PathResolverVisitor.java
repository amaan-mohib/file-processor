package com.example.fileprocessor.engine.grammar;

import com.example.fileprocessor.engine.grammar.gen.FileQueryParser;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public record PathResolverVisitor(Map<String, Object> root) {
    public static class Target {
        public Object parent;
        public String key;
        public Integer index;
        public Object current;
    }

    public Target visitPathExpression(FileQueryParser.PathExpressionContext ctx, boolean createIfMissing) {
        Object current = root;
        Object parent = root;

        String key = ctx.identifier(0).ID().getText();
        current = ensureMapField(current, key, createIfMissing);
        int idIndex = 1;
        int numberIndex = 0;
        Integer index = null;
        for (int i = 1; i < ctx.children.size(); i++) {
            ParseTree child = ctx.children.get(i);
            if (child.getText().equals(".")) {
                String fieldName = ctx.identifier(idIndex).ID().getText();
                parent = current;
                key = fieldName;
                index = null;
                current = ensureMapField(current, fieldName, createIfMissing);
                idIndex++;
            } else if (child.getText().equals("[")) {
                String indexText = ctx.NUMBER(numberIndex).getText();
                int idx = Integer.parseInt(indexText);
                if (!(current instanceof List) && createIfMissing) {
                    List<Object> temp = new ArrayList<>();
                    temp.add(current);
                    current = temp;
                    ((Map<String, Object>) parent).put(key, current);
                }
                parent = current;
                index = idx;
                key = null;
                current = ensureListIndex(current, idx, createIfMissing);
                numberIndex++;
            }
        }

        Target target = new Target();
        target.parent = parent;
        target.key = key;
        target.index = index;
        target.current = current;

        return target;
    }

    private Object ensureMapField(Object parent, String key, boolean createIfMissing) {
        if (!(parent instanceof Map)) {
            throw new IllegalStateException("Expected map, found: " + parent + ". Make sure the path is valid.");
        }

        Map<String,Object> map = (Map<String, Object>) parent;

        if (!createIfMissing) {
            return map.get(key);
        }

        return map.computeIfAbsent(key, _ -> new HashMap<>());
    }

    private Object ensureListIndex(Object parent, int idx, boolean createIfMissing) {
        if (!(parent instanceof List)) {
            throw new IllegalStateException("Expected list, found: " + parent + ". Make sure the path is valid.");
        }

        List<Object> list = (List<Object>) parent;

        if (!createIfMissing) {
            if (idx >= 0 && idx < list.size()) {
                return list.get(idx);
            } else {
                return null;
            }
        }

        while (list.size() <= idx) {
            list.add(new HashMap<>());
        }

        if (list.get(idx) == null)
            list.set(idx, new HashMap<>());

        return list.get(idx);
    }
}
