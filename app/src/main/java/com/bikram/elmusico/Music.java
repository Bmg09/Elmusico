package com.bikram.elmusico;

import android.net.Uri;

public class Music {
    String m_name;
    String a_name;
    String url;
    Uri uri;
    public Music(String m_name, String a_name,String url) {
        this.m_name = m_name;
        this.a_name = a_name;
        this.url = url;
    }

    public Music(String m_name, String a_name, Uri uri) {
        this.m_name = m_name;
        this.a_name = a_name;
        this.uri = uri;
    }
}
