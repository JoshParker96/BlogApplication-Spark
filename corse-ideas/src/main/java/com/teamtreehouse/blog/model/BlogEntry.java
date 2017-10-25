package com.teamtreehouse.blog.model;

import com.github.slugify.Slugify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlogEntry {

    private String title;
    private LocalDateTime date;
    private String entry;
    private List<CommentEntry> comments;
    private String slug;

    public BlogEntry(String title, String entry, LocalDateTime date) {
        this.entry = entry;
        this.title = title;
        this.date = date;
        comments = new ArrayList<>();
        try {
            Slugify slugify = new Slugify();
            slug = slugify.slugify(title);
        } catch (IllegalArgumentException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getTitle() {

        return title;
    }

    public String getEntry() {

        return entry;
    }

    public LocalDateTime getDate() {

        return date;
    }

    public List<CommentEntry> getComments() {

        return comments;
    }

    public String getSlug() {
        return slug;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setEntry(String entry) {

        this.entry = entry;
    }

    public void setDate(LocalDateTime date) {

        this.date = date;
    }

    public boolean addComment(CommentEntry comment){

        return comments.add(comment);
    }

    @Override
    public String toString() {

        return this.title + " " + this.entry + " " + this.date + " " + this.comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogEntry that = (BlogEntry) o;

        if (entry != null ? !entry.equals(that.entry) : that.entry != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = entry != null ? entry.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}