import { BlogpostService } from './blogpost.service';
import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MyBlog';
  service;

  constructor(service: BlogpostService) {
    this.service = service;
  }

  getBlogPosts() {
    return this.service.getBlogPosts();
  }
}
