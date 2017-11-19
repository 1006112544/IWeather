package com.daobao.asus.iweather.net.DownLoad;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import com.daobao.asus.iweather.net.CallBack.IRequest;
import com.daobao.asus.iweather.net.CallBack.ISuccess;
import com.daobao.asus.iweather.util.file.FileUtil;
import java.io.File;
import java.io.InputStream;
import okhttp3.ResponseBody;

/**
 * Created by db on 2017/10/30.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File>{
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private Context context;
    public SaveFileTask(IRequest request, ISuccess success,Context context) {
        REQUEST = request;
        SUCCESS = success;
        this.context = context;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody)params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if(downloadDir==null||downloadDir.equals(""))
        {
            downloadDir = "download_loads";
        }
        if(extension==null||extension.equals(""))
        {
            extension="";//先不做处理 以后需要时使用
        }
        if(name==null||name.equals(""))
        {
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }
        else return FileUtil.writeToDisk(is,downloadDir,name,extension);
    }
    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        //加载完毕回到主线程的方法
        super.onPreExecute();
        if(SUCCESS!=null)
        {
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null)
        {
            REQUEST.onRequsetEnd();
        }
        //如果是apk文件直接安装
        autoInstallApk(file);
    }
    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(install);
        }
    }
}
