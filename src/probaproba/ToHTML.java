/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probaproba;

import GSILabs.BModel.Artist;
import GSILabs.BModel.Collective;
import GSILabs.BModel.Concert;
import GSILabs.BModel.Exhibition;
import GSILabs.BModel.FechaCompleta;
import GSILabs.BModel.Festival;
import GSILabs.BModel.Location;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author mirya
 */
public class ToHTML {
    
    public String listOfEvents () {
        return ("<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                + "<head><h1>List of events</h1></head><body>" + 
                "<h3><a href='/concerts'>Concerts</a></h3>" +
                "<h3><a href='/exhibitions'>Exhibitions</a></h3>" + 
                "<h3><a href='/festivals'>Festivals</a></h3></body></html>");
    }
    
    public String listOfConcerts (HashMap concerts) {
        String html;
        if (concerts != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                + "<head><h1>List of concerts</h1></head><body><ul>";
            Iterator i = concerts.values().iterator();
            while (i.hasNext()) {
                String cName = ((Concert)i.next()).getName();
                html = html.concat("<li><a href='concert?concertName=" + quitarEspacios(cName) + "'>" + 
                        cName + "</a></li>");
            }
            html = html.concat("</ul></body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>There are no concerts available</h4></body></html>";
        return html;
    }
    
    public String listOfExhibitions (HashMap exhibitions) {
        String html;
        if (exhibitions != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                + "<head><h1>List of exhibitions</h1></head><body><ul>";
            Iterator i = exhibitions.values().iterator();
            while (i.hasNext()) {
                String eName = ((Exhibition)i.next()).getName();
                html = html.concat("<li><a href='exhibition?exhibitionName=" + quitarEspacios(eName)
                        + "'>" + eName + "</a></li>");
            }
            html = html.concat("</ul></body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>There are no exhibitions available</h4></body></html>";
        return html;
    }
    
    public String listOfFestivals (HashMap festivals) {
        String html;
        if (festivals != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                + "<head><h1>List of festivals</h1></head><body><ul>";
            Iterator i = festivals.values().iterator();
            while (i.hasNext()) {
                String fName = ((Festival)i.next()).getName();
                html = html.concat("<li><a href='festival?festivalName=" + quitarEspacios(fName)
                        + "'>" + fName + "</a></li>");
            }
            html = html.concat("</ul></body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>There are no festivals available</h4></body></html>";
        return html;
    }
    
    public String concertToHTML (Concert c) {
        String html;
        if (c != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                + "<head><h1>Concert</h1></head><body>Name: " + c.getName() + "<br>Performer: ";
            if (c.getPerformer() instanceof Artist)
                html = html.concat("<a href='/artist?artistName=" + quitarEspacios(c.getPerformer().getName())
                        + "'>" + c.getPerformer().getName() + "</a><br>");
            else
                html = html.concat("<a href='/collective?collectiveName=" + quitarEspacios(c.getPerformer().getName()) 
                        + "'>" + c.getPerformer().getName() + "</a><br>");
            html = html.concat("Date: " + ((FechaCompleta)c.getStartDate()).fechaToString() + 
                    "<br>Door opening: " + ((FechaCompleta)c.getDoorOpeningTimeConcert()).horaToString() 
                    + "h<br>Start time: " + ((FechaCompleta)c.getStartTimeConcert()).horaToString() + 
                    "h<br>Closing time: " + ((FechaCompleta)c.getClosingTimeConcert()).horaToString() + 
                    "h<br>Location: " + "<a href='/location?locationName=" + quitarEspacios(c.getLocation().getName())
                    + "'>" + c.getLocation().getName() + "</a><br>" + "</body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>Concert not found</h4></body></html>";
        return html;
    }
    
    public String exhibitionToHTML (Exhibition e) {
        String html;
        if (e != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<head><h1>Exhibition</h1></head><body>Name: " + e.getName() + "<br>Title: "
                    + e.getTitle() + "<br>Organizer: " + e.getOrganizerName() + "<br>Start date: " 
                    + ((FechaCompleta)e.getStartDate()).fechaToString() + "<br>Start time: " 
                    + ((FechaCompleta)e.getStartTimeExhibition()).horaToString() + "h<br>Closing time: " 
                    + ((FechaCompleta)e.getClosingTimeExhibition()).horaToString() + "h<br>Closing date: "
                    + ((FechaCompleta)e.getEndingDate()).fechaToString() + "<br>";
            if (e.getPerformer() instanceof Artist)
                html = html.concat("<a href='/artist?artistName=" + quitarEspacios(e.getPerformer().getName())
                        + "'>" + e.getPerformer().getName() + "</a><br>Web links: ");
            else
                html = html.concat("<a href='/collective?collectiveName=" + quitarEspacios(e.getPerformer().getName())
                        + "'>" + e.getPerformer().getName() + "</a><br>Web links: ");
            Iterator i = e.getWebLinks().iterator();
            while (i.hasNext()) {
                html = html.concat(i.next() + "  ");
            }
            html = html.concat("<br>Location: <a href='/location?locationName=" + 
                    quitarEspacios(e.getLocation().getName()) + "'>" + e.getLocation().getName() 
                    + "</a><br></body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>Exhibition not found</h4></body></html>";
        return html;
    }
    
    public String festivalToHTML (Festival f) {
        String html;
        if (f != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<head><h1>Festival</h1></head><body>Name: " + f.getName() + "<br>Start date: " 
                    + ((FechaCompleta)f.getStartDate()).fechaToString() + "<br>Start time: " 
                    + ((FechaCompleta)f.getStartTimeFestival()).horaToString() + "h<br>Closing time: " 
                    + ((FechaCompleta)f.getClosingTimeFestival()).horaToString() + "h<br>Closing date: "
                    + ((FechaCompleta)f.getEndingDate()).fechaToString() + "<br>";
            Iterator i = f.getConcerts().iterator();
            int j = 1;
            while (i.hasNext()) {
                String cName = ((Concert)i.next()).getName();
                html = html.concat("Concierto " + j + ": <a href='/concert?concertName=" + 
                        quitarEspacios(cName) + "'>" + cName + "</a><br>");
                j++;
            }
            html = html.concat("</body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>Festival not found</h4></body></html>";
        return html;
    }
    
    public String locationToHTML (Location l) {
        String html;
        if (l != null)
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<head><h1>Location</h1></head><body>Name: " + l.getName() + "<br>Maximum"
                    + " capacity: " + l.getMaxCapacity() + "<br>Geographical position: " + 
                    l.getGeographicPosition() + "<br>Website: " + l.getWebSite() + "<br></body></html>";
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>Location not found</h4></body></html>";
        return html;
    }
    
    public String artistToHTML (Artist a) {
        String html;
        if (a != null)
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<head><h1>Artist</h1></head><body>Name: " + a.getName() + "<br>Work description: "
                    + a.getWorkDescription() + "<br>Website: " + a.getWebSite() + "<br></body></html>";
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>Artist not found</h4></body></html>";
        return html;
    }
    
    public String collectiveToHTML (Collective c) {
        String html;
        if (c != null) {
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<head><h1>Collective</h1></head><body>Name: " + c.getName() + "<br>Work "
                    + "description: " + c.getWorkDescription() + "<br>Website: " + c.getWebSite()
                    + "<br>";
                    
            Iterator i = c.getArtists().iterator();
            int j = 1;
            while (i.hasNext()) {
                String aName = ((Artist)i.next()).getName();
                html = html.concat("Artist " + j + ": <a href='/artist?artistName=" + 
                        quitarEspacios(aName) + "'>" + aName + "</a><br>");
                j++;
            }
            html = html.concat("</body></html>");
        }
        else
            html = "<html><meta http-equiv=\'Content-Type\' content=\'text/html; charset=UTF-8\' />"
                    + "<body><h4>Collective not found</h4></body></html>";
        return html;
    }
    
    //Sustituir los espacios por guiones bajos para poder crear los enlaces
    public String quitarEspacios (String enlace) {
        return enlace.replaceAll(" ", "_");
    }
    
    
}
