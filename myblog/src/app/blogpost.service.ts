import { Http } from '@angular/http';
import { BlogPostModel } from './models/blogpost.model';
import { Injectable } from '@angular/core';

@Injectable()
export class BlogpostService {

  blogPosts: BlogPostModel[] = new Array();

  constructor(private http: Http) {
    http.get("/myblog_server/posts").subscribe(
      response => {
        for (let entry of response.json()) {
          this.blogPosts.push(new BlogPostModel(entry.title, entry.author.name,
            new Date(entry.date), entry.content));
        }
      }
    )
   }

  getBlogPosts() {
    return this.blogPosts;
  }
}
