import { BlogPostModel } from './../models/blogpost.model';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-blogpost',
  templateUrl: './blogpost.component.html',
  styleUrls: ['./blogpost.component.css']
})
export class BlogpostComponent implements OnInit {

  @Input() 
  model;

  ngOnInit() {
  }

  getFormattedDate(): string {
    var monthNames = [
      "January", "February", "March",
      "April", "May", "June", "July",
      "August", "September", "October",
      "November", "December"
    ];
  
    let date = this.model.getDate();
    var day = this.model.date.getDate();
    var monthIndex = date.getMonth();
    var year = date.getFullYear();
  
    return day + ' ' + monthNames[monthIndex] + ' ' + year;
  }

}
