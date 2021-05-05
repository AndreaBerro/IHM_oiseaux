package com.example.projetoiseaux.ui.share.Client;

import com.example.projetoiseaux.Bird.UploadBird;

import java.util.List;

public interface IUploadActivity {
    void setUploadList(List<UploadBird> uploadList);
    void resetFilter();
    List<UploadBird> getUploadList();
    String getFilter();
}
