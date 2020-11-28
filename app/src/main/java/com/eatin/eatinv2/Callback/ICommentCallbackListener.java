package com.eatin.eatinv2.Callback;

import com.eatin.eatinv2.Model.CommentModel;

import java.util.List;

public interface ICommentCallbackListener {
    void onCommentLoadSuccess(List<CommentModel> commentModels);
    void onCommentLoadFailure(String message);
}
