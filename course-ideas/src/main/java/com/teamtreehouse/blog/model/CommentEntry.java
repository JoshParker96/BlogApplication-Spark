package com.teamtreehouse.blog.model;

public class CommentEntry {

    private String name;
    private String comment;

    public CommentEntry(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public String getName() {

        return name;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return this.name + ": " + this.comment;
    }
}
