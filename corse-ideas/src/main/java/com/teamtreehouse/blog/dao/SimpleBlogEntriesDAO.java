package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.ArrayList;
import java.util.List;

public class SimpleBlogEntriesDAO implements BlogEntriesDAO {

    private List<BlogEntry> blogEntries;

    public SimpleBlogEntriesDAO() {
        blogEntries = new ArrayList<>();
    }

    @Override
    public Boolean addEntry(BlogEntry entry) {
        return blogEntries.add(entry);
    }

    @Override
    public List<BlogEntry> findALl() {
        return new ArrayList<>(blogEntries);
    }

    @Override
    public BlogEntry findSlugByEntry(String slug) {
        return blogEntries.stream()
                .filter(blogEntry -> blogEntry.getSlug().equals(slug))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }
}