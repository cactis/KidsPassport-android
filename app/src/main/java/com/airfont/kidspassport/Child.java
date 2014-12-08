package com.airfont.kidspassport;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ctslin on 12/5/14.
 */
@Table(name = "Child")
public class Child extends Model {

  private static final Logger logger = LoggerFactory.getLogger(Child.class);

  @Column(name = "Fullname")
  public String fullname;
  @Column(name = "Birthday")
  public String birthday;
  @Column(name = "Nickname")
  public String nickname;
  @Column(name = "Gender")
  public String gender;
  //@Column(name = "Avatar")
  //public byte[] avatar;
  @Column(name = "Filename")
  public String filename;

  public Child() {
    super();
  }
  //
  //public void setAvatar(Bitmap bmp) {
  //  String sFilename = new SimpleDateFormat("yyyyMMddhhmm'.jpeg'").format(new Date());
  //  logger.info(String.format("sFilename: %s", sFilename));
  //  saveToInternalSorage(bmp, sFilename);
  //  this.filename = sFilename;
  //}

  public Bitmap getAvatar() {
    return loadImageFromStorage(this.filename);
  }

  public static Child find(int id) {
    return new Select().from(Child.class).where("id = ?", id).executeSingle();
  }

  public static List<Child> all() {
    return new Select()
      .from(Child.class)
      .orderBy("fullname ASC")
      .execute();
  }

  public static int count() {
    return new Select().from(Child.class).count();
  }

  public static int columnCount() {
    Cursor c = Cache.openDatabase().query("Child", null, null, null, null, null, null);
    int count = c.getColumnCount();
    c.close();
    return count;
  }



  private Bitmap loadImageFromStorage(String filename) {
    if (filename != null) {
      try {
        logger.info(String.format("filename: %s", filename));
        File f = new File(filename);
        Bitmap bmp = BitmapFactory.decodeStream(new FileInputStream(f));
        return bmp;
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      logger.info("no avatar!");
      return null;
    }
    return null;
  }
}


//public static Child getRandom() {
//  return new Select().from(Child.class).orderBy("RANDOM()").executeSingle();
//}

//public Child create(Hashtable<String, String> attrs) {
//  this.fullname = attrs.get("fullname");
//  this.gender = attrs.get("gender");
//  //this.avatar = attrs.get("avatar");
//  this.save();
//  logger.info(String.valueOf(Child.count()));
//  return this;
//}

