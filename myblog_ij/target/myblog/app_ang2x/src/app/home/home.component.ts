import { Component, OnInit } from '@angular/core';

import { BlogPostModel } from '../models/blogpost.model';
import { BlogpostService } from '../services/blogpost.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private blogPosts: BlogPostModel[] = new Array();

  constructor(private service: BlogpostService) { }

  ngOnInit() {
    this.service.getBlogPosts().subscribe(
      response => {
        for (let entry of response.json()) {
          this.blogPosts.push(new BlogPostModel(entry.title, entry.author.name,
            new Date(entry.date), entry.content));
        }
      },
      (error: Response) => {
        if (error.status === 404) {
          //display some error to user
        }
        else {
          // display unexpected error for user
        }
      }
    )
  }

  getBlogPosts() {
    return this.blogPosts;
  }

}
