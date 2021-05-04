package com.example.projetoiseaux.ui;

import java.util.List;

public interface IUploadActivity {
    void setUploadList(List<UploadBird> uploadList);
    void resetFilter();
    List<UploadBird> getUploadList();
    String getFilter();
}
