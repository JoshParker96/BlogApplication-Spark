package com.teamtreehouse.blog.dao;

import com.teamtreehouse.blog.model.BlogEntry;

import java.util.List;

public interface BlogEntriesDAO {
    Boolean addEntry(BlogEntry entry);

    List<BlogEntry> findALl();

    BlogEntry findSlugByEntry(String slug);
}
