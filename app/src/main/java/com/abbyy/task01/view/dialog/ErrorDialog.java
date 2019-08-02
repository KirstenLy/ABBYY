package com.abbyy.task01.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.abbyy.task01.R;

import org.jetbrains.annotations.NotNull;

public class ErrorDialog extends DialogFragment {

    private static String ARG_KEY_ERROR_MESSAGE = "errorMsg";

    public ErrorDialog() {
    }

    public static ErrorDialog getInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY_ERROR_MESSAGE, message);

        ErrorDialog errorDialog = new ErrorDialog();
        errorDialog.setArguments(bundle);
        return errorDialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_error_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String errorMessage = (getArguments() == null) ? "Unknown" : getArguments().getString(ARG_KEY_ERROR_MESSAGE, "Unknown");

        TextView txtError = view.findViewById(R.id.txt_error);
        Button btnExit = view.findViewById(R.id.btn_close_app);

        txtError.setText(getString(R.string.dialogs_error_network_connection, errorMessage));
        btnExit.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().finishAffinity();
            }
        });
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getActivity() != null) {
            Dialog dialog = new Dialog(getActivity(), R.style.Dialog_DialogFragment);
            dialog.setCancelable(false);
            return dialog;
        } else {return super.onCreateDialog(savedInstanceState);}
    }
}
