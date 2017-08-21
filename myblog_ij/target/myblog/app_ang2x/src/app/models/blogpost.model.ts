export class BlogPostModel {

  constructor(private title: string, private author: string,
    private date: Date, private content: string) {}

  getTitle(): string {
    return this.title;
  }

  getAuthor(): string {
    return this.author;
  }

  getDate(): Date {
    return this.date;
  }

  getContent(): string {
    return this.content;
  }

}
