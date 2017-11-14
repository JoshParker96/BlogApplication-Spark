package  com.teamtreehouse.blog;

import com.teamtreehouse.blog.dao.BlogEntriesDAO;
import com.teamtreehouse.blog.dao.SimpleBlogEntriesDAO;
import com.teamtreehouse.blog.model.BlogEntry;
import com.teamtreehouse.blog.model.CommentEntry;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {

        BlogEntriesDAO blogDao = new SimpleBlogEntriesDAO();

        before("/new", (rq, rs) -> {
            if (rq.cookie("admin") == null) {
                rs.redirect("/sign-in");
                halt();
            }
        });

        before("/edit/:slug", (rq, rs) -> {
            if (rq.cookie("admin") == null) {
                rs.redirect("/sign-in");
                halt();
            }
        });

        get("/", (rq, rs) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogs", blogDao.findALl());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/new", (rq, rs) -> {
            return new ModelAndView(null, "new.hbs");
        }, new HandlebarsTemplateEngine());

        post("/new", (rq, rs) -> {
            String title = rq.queryParams("title");
            String entry = rq.queryParams("entry");
            BlogEntry blogEntry = new BlogEntry(title, entry, LocalDateTime.now());
            blogDao.addEntry(blogEntry);
            Map<String, Object> model = new HashMap<>();
            model.put("blogs", blogDao.findALl());
            return new ModelAndView(model, "index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/detail/:slug", (rq, rs) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogs", blogDao.findSlugByEntry(rq.params("slug")));
            return new ModelAndView(model, "detail.hbs");
        }, new HandlebarsTemplateEngine());

        get("/sign-in", (rq, rs) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogs", blogDao.findALl());
            return new ModelAndView(model, "sign-in.hbs");
        }, new HandlebarsTemplateEngine());

        post("/sign-in", (rq, rs) -> {
            String username = rq.queryParams("username");
            rs.cookie(username, "username");
            return new ModelAndView(null, "index.hbs");
        }, new HandlebarsTemplateEngine());

        post("/addComment/:slug", (rq, rs) -> {
            String name = rq.queryParams("username");
            String userComment = rq.queryParams("comment");
            CommentEntry comment = new CommentEntry(name, userComment);
            BlogEntry blogEntry = blogDao.findSlugByEntry(rq.params("slug"));
            blogEntry.addComment(comment);
            rs.redirect("/detail/" + blogEntry.getSlug());
            return null;
        });

        get("/edit/:slug", (rq, rs) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("blogs", blogDao.findSlugByEntry(rq.params("slug")));
            return new ModelAndView(model, "edit.hbs");
        }, new HandlebarsTemplateEngine());

        post("/edit/:slug", (rq, rs) -> {
            String title = rq.queryParams("title");
            String entry = rq.queryParams("entry");
            BlogEntry blogEntry = blogDao.findSlugByEntry(rq.params("slug"));
            blogEntry.setTitle(title);
            blogEntry.setEntry(entry);
            rs.redirect("/detail/" + blogEntry.getSlug());
            return null;
        });
    }
}