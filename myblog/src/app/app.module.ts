import { BlogpostService } from './blogpost.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { BlogpostComponent } from './blogpost/blogpost.component';

@NgModule({
  declarations: [
    AppComponent,
    BlogpostComponent
  ],
  imports: [
    BrowserModule,
    HttpModule
  ],
  providers: [
    BlogpostService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
