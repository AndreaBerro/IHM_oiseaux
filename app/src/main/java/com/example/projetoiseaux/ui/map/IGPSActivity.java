package com.example.projetoiseaux.ui.map;

public interface IGPSActivity {
    int REQUEST_PERMISSION_GPS = 400;
    void updateGps(MapFragment mapFragment);
    void moveCamera();  // move camera (and zoom ) to center the mao to the GPS position
}
