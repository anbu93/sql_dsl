package com.vova_cons.jdbc_dsl.dsl;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Created by anbu on 17.09.18.
 **/
public class StringTemplate {
    private List<Node> nodes;

    public StringTemplate(String template) {
        //TODO crutch method, this is fucking regexp in String.replaceAll metrhod!!!1
        nodes = new LinkedList<>();
        StringBuilder buffer = new StringBuilder();
        for(char c : template.toCharArray()) {
            if (c == '{') {
                nodes.add(new Node(buffer.toString(), false));
                buffer = new StringBuilder();
            }
            buffer.append(c);
            if (c == '}') {
                nodes.add(new Node(buffer.toString(), true));
                buffer = new StringBuilder();
            }
        }
        String last = buffer.toString();
        if (!"".equals(last)) {
            nodes.add(new Node(last, false));
        }
    }
    public StringTemplate(List<Node> nodes) {
        this.nodes = new LinkedList<>();
        for(Node node : nodes) {
            this.nodes.add(new Node(node.getValue(), node.isPattern()));
        }
    }
    public void setNode(String key, String value) {
        for(Node node : nodes) {
            if (node.isPattern() && node.getValue().equals("{" + key + "}")) {
                node.setValue(value);
            }
        }
    }

    public StringTemplate set(String key, Object value) {
        StringTemplate newTemplate = new StringTemplate(nodes);
        newTemplate.setNode(key, Objects.toString(value));
        return newTemplate;
    }

    public String get() {
        StringBuilder sb = new StringBuilder();
        for(Node node : nodes) {
            sb.append(node.getValue());
        }
        return sb.toString();
    }

    private static class Node {
        private boolean isPattern;
        private String value;

        public Node(String value, boolean isPattern) {
            this.value = value;
            this.isPattern = isPattern;
        }

        public boolean isPattern() {
            return isPattern;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

