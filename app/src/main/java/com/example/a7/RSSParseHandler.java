package com.example.a7;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class RSSParseHandler extends DefaultHandler {

    Boolean inItem = false;
    Boolean inTitle = false;
    Boolean inPubDate = false;
    Boolean inDescription = false;
    StringBuilder sb;

    public List<News> newsItems = new ArrayList<>();
    News item;

    String exTitle;
    String exPubDate;
    String exDescription;


    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        Log.d("JN", "START of DOC ");
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        Log.d("JN", "END of DOC - ");

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        Log.d("JN", "START ELM - " + qName);


        if(qName.equals("item")){
            // DO SOMETHING // CREATE A OBJECT TO STORE DATA or CREATE A EMPTY array list
            // Title.. Date.. Description
            item = new News();
            inItem = true;
//            sb = new StringBuilder();
        }else if(qName.equals("title")){
            inTitle = true;
            sb = new StringBuilder();

        }else if(qName.equals("pubDate")){
            inPubDate = true;
            sb = new StringBuilder();

        }else if(qName.equals("description")){
            inDescription = true;
            sb = new StringBuilder();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        Log.d("JN", "END ELM - " + qName);

        if(qName.equals("item")){
            // DO SOMETHING // CREATE A OBJECT TO STORE DATA or CREATE A EMPTY array list
            // Title.. Date.. Description

            newsItems.add(item);
            inItem = false;
        }else if(inItem && qName.equals("title")){
            inTitle = false;
            item.setTitle(sb.toString());
                        Log.d("JN", "Content TITLE - " + sb);
//            exTitle = sb.toString();
//            News newsItem = new News(exTitle, "sdf", "sdfds");
//            News newsItem = new News(exTitle, exPubDate,exDescription);
//            newsItems.add(newsItem);
//            News.setTitle(sd);
//            News.generateNews().add(new News(sb));
        }else if(inItem && qName.equals("pubDate")){
            inPubDate = false;
            item.setPubDate(sb.toString());
            Log.d("JN", "Content DATE - " + sb);
//            exPubDate = sb.toString();
//            News newsItem = new News(exTitle,exPubDate,exDescription);
//            newsItems.add(newsItem);
        }else if(inItem && qName.equals("description")){
            inDescription = false;
            item.setDescription(sb.toString());
            Log.d("JN", "Content DESCRIPTION - " + sb);
//            exDescription = sb.toString();
//            News newsItem = new News(exTitle,exPubDate,exDescription);
//            newsItems.add(newsItem);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
//        String characterString = new String(ch, start, length);
        if (inItem && inTitle){
            // Print the character string or do something..
            sb.append(ch, start, length);
//            Log.d("JN", "Content TITLE - " + characterString);
        }else if (inItem && inPubDate){
            sb.append(ch, start, length);
//            Log.d("JN", "Content DATE - " + characterString);
        }else if (inItem && inDescription){

            sb.append(ch, start, length);
//            Log.d("JN", "Content DESCRIPTION - " + characterString);
        }
        // Log.d("JN", "Content - " + characterString);
    }

    public List<News> getItems() { return newsItems; }
}













//    public void characters(char[] ch, int start, int length) throws SAXException {
//        super.characters(ch, start, length);
////        String characterString = new String(ch, start, length);
//        if (inItem && inTitle){
//            // Print the character string or do something..
//            sb.append(ch, start, length);
////            Log.d("JN", "Content TITLE - " + characterString);
//        }else if (inItem && inPubDate){
//            sb.append(ch, start, length);
////            Log.d("JN", "Content DATE - " + characterString);
//        }else if (inItem && inDescription){
//
//            sb.append(ch, start, length);
////            Log.d("JN", "Content DESCRIPTION - " + characterString);
//        }
//        // Log.d("JN", "Content - " + characterString);
//    }
//
//    public ArrayList<News> getItems() { return newsItems; }
