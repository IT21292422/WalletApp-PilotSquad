// Generated by view binder compiler. Do not edit!
package com.example.project_bank.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.project_bank.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTransactionViewBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView bankTransactName;

  @NonNull
  public final ImageView creditBtn;

  @NonNull
  public final ImageView debitBtn;

  @NonNull
  public final RecyclerView recyclerView;

  @NonNull
  public final Button searchBtn;

  @NonNull
  public final SearchView searchTransaction;

  @NonNull
  public final TableLayout tableLayout;

  @NonNull
  public final TableLayout tableSummary;

  @NonNull
  public final TextView textView4;

  @NonNull
  public final TextView totBalance;

  @NonNull
  public final TextView totCredits;

  @NonNull
  public final TextView totDebits;

  private ActivityTransactionViewBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView bankTransactName, @NonNull ImageView creditBtn, @NonNull ImageView debitBtn,
      @NonNull RecyclerView recyclerView, @NonNull Button searchBtn,
      @NonNull SearchView searchTransaction, @NonNull TableLayout tableLayout,
      @NonNull TableLayout tableSummary, @NonNull TextView textView4, @NonNull TextView totBalance,
      @NonNull TextView totCredits, @NonNull TextView totDebits) {
    this.rootView = rootView;
    this.bankTransactName = bankTransactName;
    this.creditBtn = creditBtn;
    this.debitBtn = debitBtn;
    this.recyclerView = recyclerView;
    this.searchBtn = searchBtn;
    this.searchTransaction = searchTransaction;
    this.tableLayout = tableLayout;
    this.tableSummary = tableSummary;
    this.textView4 = textView4;
    this.totBalance = totBalance;
    this.totCredits = totCredits;
    this.totDebits = totDebits;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTransactionViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTransactionViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_transaction_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTransactionViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bankTransactName;
      TextView bankTransactName = ViewBindings.findChildViewById(rootView, id);
      if (bankTransactName == null) {
        break missingId;
      }

      id = R.id.creditBtn;
      ImageView creditBtn = ViewBindings.findChildViewById(rootView, id);
      if (creditBtn == null) {
        break missingId;
      }

      id = R.id.debitBtn;
      ImageView debitBtn = ViewBindings.findChildViewById(rootView, id);
      if (debitBtn == null) {
        break missingId;
      }

      id = R.id.recyclerView;
      RecyclerView recyclerView = ViewBindings.findChildViewById(rootView, id);
      if (recyclerView == null) {
        break missingId;
      }

      id = R.id.searchBtn;
      Button searchBtn = ViewBindings.findChildViewById(rootView, id);
      if (searchBtn == null) {
        break missingId;
      }

      id = R.id.searchTransaction;
      SearchView searchTransaction = ViewBindings.findChildViewById(rootView, id);
      if (searchTransaction == null) {
        break missingId;
      }

      id = R.id.tableLayout;
      TableLayout tableLayout = ViewBindings.findChildViewById(rootView, id);
      if (tableLayout == null) {
        break missingId;
      }

      id = R.id.tableSummary;
      TableLayout tableSummary = ViewBindings.findChildViewById(rootView, id);
      if (tableSummary == null) {
        break missingId;
      }

      id = R.id.textView4;
      TextView textView4 = ViewBindings.findChildViewById(rootView, id);
      if (textView4 == null) {
        break missingId;
      }

      id = R.id.totBalance;
      TextView totBalance = ViewBindings.findChildViewById(rootView, id);
      if (totBalance == null) {
        break missingId;
      }

      id = R.id.totCredits;
      TextView totCredits = ViewBindings.findChildViewById(rootView, id);
      if (totCredits == null) {
        break missingId;
      }

      id = R.id.totDebits;
      TextView totDebits = ViewBindings.findChildViewById(rootView, id);
      if (totDebits == null) {
        break missingId;
      }

      return new ActivityTransactionViewBinding((ConstraintLayout) rootView, bankTransactName,
          creditBtn, debitBtn, recyclerView, searchBtn, searchTransaction, tableLayout,
          tableSummary, textView4, totBalance, totCredits, totDebits);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
