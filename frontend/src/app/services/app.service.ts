import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Comment } from '../models/Comment';
import { User } from '../models/User';
import { Book } from '../models/Book';
import { Observable } from 'rxjs';

import { catchError } from 'rxjs/operators';
import {Favourite} from '../models/Favourite';
import {Weather} from '../models/Weather';

@Injectable()
export class AppService {
  private headers = new HttpHeaders({'Content-Type': 'application/json'
                                              // Accept: 'application/json'
                                             // Origin: 'localhost'
                                             // 'Access-Control-Allow-Origin': '*',
                                             // 'Access-Control-Allow-Credentials': 'true',
                                             // 'Access-Control-Allow-Headers': 'origin, content-type, accept, authorization, x-auth-token',
                                             //  'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS, HEAD'
     });
  // private booksUrl = 'http://34.77.125.207/v1/books';
  // private booksUrl = '/book/v1/books';
  private booksUrl = 'http://' + location.hostname + ':8080/v1/books';
  private commentsUrl = 'http://' + location.hostname + ':8081/v1/comments';
  private interfaceUrl = 'http://' + location.hostname + ':8082/v1/app';

  constructor(private http: HttpClient) { }

  getAllBooks(): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl, {headers: this.headers}).pipe(catchError(this.handleError));
  }

  getBook(id: number): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + '/' + id, {headers: this.headers}).pipe(catchError(this.handleError));
  }

  getFavBooks(userId: number): Observable<Book[]> {
    return this.http.get<Book[]>(this.interfaceUrl + '/getfavouritebooks/' + userId,
                            {headers: this.headers}).pipe(catchError(this.handleError));
  }

  getSearchBooks(search: string): Observable<Book[]> {
    return this.http.get<Book[]>(this.booksUrl + '/search/' + search,
      {headers: this.headers}).pipe(catchError(this.handleError));
  }

  addToFavourites(favourite: Favourite): Observable<Response> {
    return this.http.post(this.interfaceUrl + '/addfavourite', JSON.stringify(favourite), {headers: this.headers})
                          .pipe(catchError(this.handleAddFavouriteError));
    // return this.http.get(this.interfaceUrl + '/addfavourite/' + favourite.favourite.userId + ',' + favourite.favourite.bookId,
    //   {headers: this.headers}).pipe(catchError(this.handleAddFavouriteError));
  }

  getCommentsOfBook(book: Book): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.commentsUrl + '/ofbook/' + book.book.id,
      {headers: this.headers}).pipe(catchError(this.handleError));
  }

  postComment(comment: Comment): Observable<Response> {
    return this.http.post(this.interfaceUrl + '/addcomment', JSON.stringify(comment), {headers: this.headers})
      .pipe(catchError(this.handleAddFavouriteError));
  }

  getUser(user: number): Observable<User> {
    return this.http.get<User>(this.interfaceUrl + '/getuser/' + user,
      {headers: this.headers}).pipe(catchError(this.handleLoginError));
  }

  login(user: User): Observable<User> {
    return this.http.get<User>(this.interfaceUrl + '/login/' + user.user.username + ',' + user.user.password,
                              {headers: this.headers}).pipe(catchError(this.handleLoginError));
  }

  getWeather(): Observable<Weather> {
    return this.http.get<Weather>(this.interfaceUrl + '/weather',
      {headers: this.headers}).pipe(catchError(this.handleLoginError));
  }

  private handleAddFavouriteError(error: any): Promise<any> {
    alert('Could not add book to favourites.');
    return Promise.reject();
  }

  private handleLoginError(error: any): Promise<any> {
    alert('Invalid username and password combination.');
    return Promise.reject();
  }

  private handleError(error: any): Promise<any> {
    console.error('An error occured while trying to communicate with services.', error);
    return Promise.reject(error.body || error);
  }
}
