package com.taskstorage.wireframe.controller;

import com.taskstorage.wireframe.domain.Feed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RssController {

    @Value("${xml.path}")
    private String xmlPath;

    @GetMapping("/feed")
    public String wodb(Model model) throws IOException {

        Iterable<Feed> feeds = parse(xmlPath);
        model.addAttribute("feeds", feeds);
        return "feed";
    }

    public List<Feed> parse(String xmlPath) throws IOException {
        List<Feed> wodblist= new ArrayList<> ();
        Feed singleFeed = null;
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new URL(xmlPath).openStream());
            while(xmlEventReader.hasNext()){
                XMLEvent xmlEvent = xmlEventReader.nextEvent();
                if (xmlEvent.isStartElement())
                {
                    StartElement startElement = xmlEvent.asStartElement();
                    if(startElement.getName().getLocalPart().equals("item"))
                    {
                        singleFeed = new Feed();
                    }
                    if (singleFeed !=null)
                    {
                        if(startElement.getName().getLocalPart().equals("guid")){
                            xmlEvent = xmlEventReader.nextEvent();
                            singleFeed.setGuid(xmlEvent.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("link"))
                        {
                            xmlEvent = xmlEventReader.nextEvent();
                            singleFeed.setLink(xmlEvent.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("title"))
                        {
                            xmlEvent = xmlEventReader.nextEvent();
                            singleFeed.setTitle(xmlEvent.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("pubDate"))
                        {
                            xmlEvent = xmlEventReader.nextEvent();
                            singleFeed.setPubdate(xmlEvent.asCharacters().getData());
                        }
                        else if(startElement.getName().getLocalPart().equals("description"))
                        {
                            xmlEvent = xmlEventReader.nextEvent();
                            singleFeed.setDescription(xmlEvent.asCharacters().getData());
                        }
                    }
                }
                if(xmlEvent.isEndElement()){
                    EndElement endElement = xmlEvent.asEndElement();
                    if(endElement.getName().getLocalPart().equals("item")){
                        wodblist.add(singleFeed);
                    }
                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
        return wodblist;
    }
}
