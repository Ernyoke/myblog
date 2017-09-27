import { Http } from '@angular/http';
import { Injectable } from '@angular/core';

@Injectable()
export class BlogpostService {

  private url: string = "/myblog/posts"

  constructor(private http: Http) { }

  getBlogPosts() {
    return this.http.get(this.url);
  }
}
