export class Comment {
  comment: CommentInfo;
}

export class CommentInfo {
  commentId: number;
  content: string;
  createdTimestamp: number;
  bookId: number;
  userId: number;
}
