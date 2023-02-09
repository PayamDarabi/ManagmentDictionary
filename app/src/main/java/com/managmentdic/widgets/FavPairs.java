package com.managmentdic.widgets;

import com.managmentdic.widgets.StringPair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Payam on 2/5/2016.
 */
public class FavPairs {
 private List<StringPair> favs;

 public List<StringPair> getFavs() {
  if(favs == null) favs = new ArrayList<StringPair>();
  return favs;
 }

 public void setFavs(List<StringPair> favs) {
  this.favs = favs;
 } }
