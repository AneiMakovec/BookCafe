import { Component, OnInit } from '@angular/core';

import {Comment, CommentInfo} from '../models/Comment';
import { AppService } from '../services/app.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import {Book, BookInfo} from '../models/Book';


@Component({
  selector: 'app-dashboard-comments',
  templateUrl: './dashboard-comments.component.html',
  styleUrls: ['./dashboard-comments.component.css']
})
export class DashboardCommentsComponent implements OnInit {
  displayedColumns = ['name', 'comment'];
  comments: Comment[];
  user: number;
  book: Book = new Book();
  comment: Comment = new Comment();

  constructor(private appService: AppService,
              private route: ActivatedRoute,
              private location: Location) { }

  ngOnInit(): void {
    this.book.book = new BookInfo();
    this.comment.comment = new CommentInfo();
    this.user = +this.route.snapshot.paramMap.get('userId');
    this.book.book.id = +this.route.snapshot.paramMap.get('bookId');
    this.book.book.author = this.route.snapshot.paramMap.get('author');
    this.book.book.title = this.route.snapshot.paramMap.get('title');
    this.book.book.description = this.route.snapshot.paramMap.get('description');
    this.book.book.numPages = +this.route.snapshot.paramMap.get('numPages');
    this.book.book.publisher = this.route.snapshot.paramMap.get('publisher');
    this.book.book.publishDate = new Date(this.route.snapshot.paramMap.get('publishDate'));
    this.getCommentsOfBook();
  }

  getCommentsOfBook(): void {
    this.appService.getCommentsOfBook(this.book).subscribe(comments => { this.comments = comments; });
  }

  addComment(): void {
    this.comment.comment.userId = this.user;
    this.comment.comment.bookId = this.book.book.id;
    this.comment.comment.createdTimestamp = + new Date();
    this.appService.postComment(this.comment).subscribe();
    this.getCommentsOfBook();
  }

  back(): void {
    this.location.back();
  }
}
