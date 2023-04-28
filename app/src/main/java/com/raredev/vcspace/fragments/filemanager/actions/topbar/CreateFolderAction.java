package com.raredev.vcspace.fragments.filemanager.actions.topbar;

import android.content.Context;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.raredev.vcspace.R;
import com.raredev.vcspace.databinding.LayoutTextinputBinding;
import com.raredev.vcspace.fragments.filemanager.FileManagerFragment;
import com.raredev.vcspace.fragments.filemanager.actions.TopbarBaseAction;
import com.vcspace.actions.ActionData;
import java.io.File;

public class CreateFolderAction extends TopbarBaseAction {

  @Override
  public void performAction(@NonNull ActionData data) {
    FileManagerFragment fragment = getFragment(data);
    File file = getFile(data);

    LayoutTextinputBinding binding =
        LayoutTextinputBinding.inflate(LayoutInflater.from(fragment.requireActivity()));
    TextInputEditText et_filename = binding.etInput;
    binding.tvInputLayout.setHint(fragment.getString(R.string.folder_name_hint));

    new MaterialAlertDialogBuilder(fragment.requireActivity())
        .setTitle(R.string.new_folder_title)
        .setPositiveButton(
            R.string.create,
            (di, witch) -> {
              File newFolder = new File(file, et_filename.getText().toString().trim());
              if (!newFolder.exists()) {
                if (newFolder.mkdirs()) {
                  fragment.refreshFiles();
                }
              }
            })
        .setNegativeButton(R.string.cancel, (di, witch) -> di.dismiss())
        .setView(binding.getRoot())
        .show();
  }

  @Override
  public String getActionId() {
    return "create.folder.action";
  }

  @Override
  public String getTitle(Context context) {
    return context.getString(R.string.new_folder_title);
  }

  @Override
  public int getIcon() {
    return R.drawable.folder_plus_outline;
  }
}