import {Component, OnInit} from '@angular/core';

import { Book } from '../models/Book';
import { AppService } from '../services/app.service';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
// import {MatSort, MatTableDataSource} from '@angular/material';
import {Sort} from '@angular/material/sort';
import {Favourite, FavouriteInfo} from '../models/Favourite';
import {Location} from '@angular/common';
import {Weather} from '../models/Weather';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  // displayedColumns = ['author', 'title', 'numPages', 'publisher'];
  user: number;
  username: string;

  weather: Weather;

  books: Book[];
  sortedData: Book[];

  search: string;

  constructor(private appService: AppService,
              private route: ActivatedRoute,
              private router: Router,
              private location: Location) { }

  ngOnInit(): void {
    this.user = +this.route.snapshot.paramMap.get('id');
    this.username = this.route.snapshot.paramMap.get('username');
    this.getWeather();
    this.getAllBooks();
  }

  getAllBooks(): void {
    this.appService.getAllBooks().subscribe(books => {
                              this.books = books;
                              if (this.books.length === 0) {
                                this.sortedData = [];
                              } else {
                                this.sortedData = this.books.slice();
                              }
    });
  }

  getFavBooks(): void {
    this.appService.getFavBooks(this.user).subscribe(books => {
      this.books = books;
      if (this.books.length === 0) {
        this.sortedData = [];
      } else {
        this.sortedData = this.books.slice();
      }
    });
  }

  addToFavourites(book: Book): void {
    const fav = new Favourite();
    // alert(JSON.stringify(book));
    fav.favourite = new FavouriteInfo();
    fav.favourite.bookId = book.book.id;
    fav.favourite.userId = this.user;
    // alert(JSON.stringify(fav));
    this.appService.addToFavourites(fav).subscribe();
  }

  toCommentPage(book: Book): void {
    this.router.navigate(['dashboard/comments',
      this.user, book.book.id, book.book.title, book.book.author,
      book.book.publisher, book.book.description, book.book.numPages, book.book.publishDate]);
  }

  searchBooks(): void {
    this.appService.getSearchBooks(this.search).subscribe(books => {
      this.books = books;
      if (this.books.length === 0) {
        this.sortedData = [];
      } else {
        this.sortedData = this.books.slice();
      }
    });
  }

  logOut(): void {
    this.location.back();
  }

  getWeather(): void {
    this.appService.getWeather().subscribe(weather => this.weather = weather);
  }

  sortData(sort: Sort) {
    const data = this.books.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }

    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'author': return compare(a.book.author, b.book.author, isAsc);
        case 'title': return compare(a.book.title, b.book.title, isAsc);
        case 'numPages': return compare(a.book.numPages, b.book.numPages, isAsc);
        case 'publisher': return compare(a.book.publisher, b.book.publisher, isAsc);
        default: return 0;
      }
    });
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
