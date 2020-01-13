export class Book {
  book: BookInfo;
}

export class BookInfo {
  id: number;
  title: string;
  author: string;
  description: string;
  numPages: number;
  publisher: string;
  publishDate: Date;
}
