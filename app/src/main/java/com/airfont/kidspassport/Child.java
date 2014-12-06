package com.airfont.kidspassport;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Hashtable;
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
  @Column(name = "Nickname")
  public String nickname;
  @Column(name = "Gender")
  public String gender;

  public static Child getRandom() {
    return new Select().from(Child.class).orderBy("RANDOM()").executeSingle();
  }

  public Child update_attributes(Hashtable<String, String> attrs) {
    this.fullname = attrs.get("fullname");
    this.save();
    logger.info(String.valueOf(Child.count()));
    return this;
  }

  public static List<Child> all() {
    return new Select()
      .from(Child.class)
      .orderBy("Fullname ASC")
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
}
