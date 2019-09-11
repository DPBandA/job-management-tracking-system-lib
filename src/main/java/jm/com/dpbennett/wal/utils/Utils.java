/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jm.com.dpbennett.wal.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import javax.faces.model.SelectItem;
import jm.com.dpbennett.business.entity.JobManagerUser;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import jm.com.dpbennett.business.entity.Employee;
import jm.com.dpbennett.business.entity.SystemOption;
import jm.com.dpbennett.business.entity.utils.ReturnMessage;

/**
 *
 * @author Desmond Bennett <info@dpbennett.com.jm at http//dpbennett.com.jm>
 */
public class Utils {

    private static ArrayList countries;

    /**
     * Gets 10 years starting with the current year. To be verified!
     *
     * @return
     */
    public List getYears() {
        List years = new ArrayList();

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (Integer i = currentYear; i > (currentYear - 10); i--) {
            years.add(new SelectItem(i, i.toString()));
        }

        return years;
    }

    public static List getPersonalTitles() {
        ArrayList titles = new ArrayList();

        titles.add(new SelectItem("--", "--"));
        titles.add(new SelectItem("Mr.", "Mr."));
        titles.add(new SelectItem("Ms.", "Ms."));
        titles.add(new SelectItem("Mrs.", "Mrs."));
        titles.add(new SelectItem("Miss", "Miss"));
        titles.add(new SelectItem("Dr.", "Dr."));

        return titles;
    }

    public static List getSexes() {
        ArrayList titles = new ArrayList();

        titles.add(new SelectItem("--", "--"));
        titles.add(new SelectItem("Male", "Male"));
        titles.add(new SelectItem("Female", "Female"));

        return titles;
    }

    // Not used. These are obtained from database. May be removed in future.
    public static List getCountries() {

        countries = new ArrayList();

        countries.add(new SelectItem("af", "Afghanistan"));
        countries.add(new SelectItem("ax", "Aland Islands"));
        countries.add(new SelectItem("al", "Albania"));
        countries.add(new SelectItem("dz", "Algeria"));
        countries.add(new SelectItem("as", "American Samoa"));
        countries.add(new SelectItem("ad", "Andorra"));
        countries.add(new SelectItem("ao", "Angola"));
        countries.add(new SelectItem("ai", "Anguilla"));
        countries.add(new SelectItem("aq", "Antarctica"));
        countries.add(new SelectItem("ag", "Antigua and Barbuda"));
        countries.add(new SelectItem("ar", "Argentina"));
        countries.add(new SelectItem("am", "Armenia"));
        countries.add(new SelectItem("aw", "Aruba"));
        countries.add(new SelectItem("au", "Australia"));
        countries.add(new SelectItem("at", "Austria"));
        countries.add(new SelectItem("az", "Azerbaijan"));
        countries.add(new SelectItem("bs", "Bahamas"));
        countries.add(new SelectItem("bh", "Bahrain"));
        countries.add(new SelectItem("bd", "Bangladesh"));
        countries.add(new SelectItem("bb", "Barbados"));
        countries.add(new SelectItem("by", "Belarus"));
        countries.add(new SelectItem("be", "Belgium"));
        countries.add(new SelectItem("bz", "Belize"));
        countries.add(new SelectItem("bj", "Benin"));
        countries.add(new SelectItem("bm", "Bermuda"));
        countries.add(new SelectItem("bt", "Bhutan"));
        countries.add(new SelectItem("bo", "Bolivia"));
        countries.add(new SelectItem("ba", "Bosnia and Herzegovina"));
        countries.add(new SelectItem("bw", "Botswana"));
        countries.add(new SelectItem("bv", "Bouvet Island"));
        countries.add(new SelectItem("br", "Brazil"));
        countries.add(new SelectItem("io", "British Indian Ocean Territory"));
        countries.add(new SelectItem("vg", "British Virgin Islands"));
        countries.add(new SelectItem("bn", "Brunei"));
        countries.add(new SelectItem("bg", "Bulgaria"));
        countries.add(new SelectItem("bf", "Burkina Faso"));
        countries.add(new SelectItem("bi", "Burundi"));
        countries.add(new SelectItem("kh", "Cambodia"));
        countries.add(new SelectItem("cm", "Cameroon"));
        countries.add(new SelectItem("ca", "Canada"));
        countries.add(new SelectItem("cv", "Cape Verde"));
        countries.add(new SelectItem("ky", "Cayman Islands"));
        countries.add(new SelectItem("cf", "Central African Republic"));
        countries.add(new SelectItem("td", "Chad"));
        countries.add(new SelectItem("cl", "Chile"));
        countries.add(new SelectItem("cn", "China"));
        countries.add(new SelectItem("cx", "Christmas Island"));
        countries.add(new SelectItem("cc", "Cocos (Keeling) Islands"));
        countries.add(new SelectItem("co", "Colombia"));
        countries.add(new SelectItem("km", "Comoros"));
        countries.add(new SelectItem("cg", "Congo"));
        countries.add(new SelectItem("ck", "Cook Islands"));
        countries.add(new SelectItem("cr", "Costa Rica"));
        countries.add(new SelectItem("hr", "Croatia"));
        countries.add(new SelectItem("cu", "Cuba"));
        countries.add(new SelectItem("cy", "Cyprus"));
        countries.add(new SelectItem("cz", "Czech Republic"));
        countries.add(new SelectItem("cd", "Democratic Republic of Congo"));
        countries.add(new SelectItem("dk", "Denmark"));
        countries.add(new SelectItem("xx", "Disputed Territory"));
        countries.add(new SelectItem("dj", "Djibouti"));
        countries.add(new SelectItem("dm", "Dominica"));
        countries.add(new SelectItem("do", "Dominican Republic"));
        countries.add(new SelectItem("tl", "East Timor"));
        countries.add(new SelectItem("ec", "Ecuador"));
        countries.add(new SelectItem("eg", "Egypt"));
        countries.add(new SelectItem("sv", "El Salvador"));
        countries.add(new SelectItem("gq", "Equatorial Guinea"));
        countries.add(new SelectItem("er", "Eritrea"));
        countries.add(new SelectItem("ee", "Estonia"));
        countries.add(new SelectItem("et", "Ethiopia"));
        countries.add(new SelectItem("fk", "Falkland Islands"));
        countries.add(new SelectItem("fo", "Faroe Islands"));
        countries.add(new SelectItem("fm", "Federated States of Micronesia"));
        countries.add(new SelectItem("fj", "Fiji"));
        countries.add(new SelectItem("fi", "Finland"));
        countries.add(new SelectItem("fr", "France"));
        countries.add(new SelectItem("gf", "French Guyana"));
        countries.add(new SelectItem("pf", "French Polynesia"));
        countries.add(new SelectItem("tf", "French Southern Territories"));
        countries.add(new SelectItem("ga", "Gabon"));
        countries.add(new SelectItem("gm", "Gambia"));
        countries.add(new SelectItem("ge", "Georgia"));
        countries.add(new SelectItem("de", "Germany"));
        countries.add(new SelectItem("gh", "Ghana"));
        countries.add(new SelectItem("gi", "Gibraltar"));
        countries.add(new SelectItem("gr", "Greece"));
        countries.add(new SelectItem("gl", "Greenland"));
        countries.add(new SelectItem("gd", "Grenada"));
        countries.add(new SelectItem("gp", "Guadeloupe"));
        countries.add(new SelectItem("gu", "Guam"));
        countries.add(new SelectItem("gt", "Guatemala"));
        countries.add(new SelectItem("gn", "Guinea"));
        countries.add(new SelectItem("gw", "Guinea-Bissau"));
        countries.add(new SelectItem("gy", "Guyana"));
        countries.add(new SelectItem("ht", "Haiti"));
        countries.add(new SelectItem("hm", "Heard Island and Mcdonald Islands"));
        countries.add(new SelectItem("hn", "Honduras"));
        countries.add(new SelectItem("hk", "Hong Kong"));
        countries.add(new SelectItem("hu", "Hungary"));
        countries.add(new SelectItem("is", "Iceland"));
        countries.add(new SelectItem("in", "India"));
        countries.add(new SelectItem("id", "Indonesia"));
        countries.add(new SelectItem("ir", "Iran"));
        countries.add(new SelectItem("iq", "Iraq"));
        countries.add(new SelectItem("xe", "Iraq-Saudi Arabia Neutral Zone"));
        countries.add(new SelectItem("ie", "Ireland"));
        countries.add(new SelectItem("il", "Israel"));
        countries.add(new SelectItem("it", "Italy"));
        countries.add(new SelectItem("ci", "Ivory Coast"));
        countries.add(new SelectItem("jm", "Jamaica"));
        countries.add(new SelectItem("jp", "Japan"));
        countries.add(new SelectItem("jo", "Jordan"));
        countries.add(new SelectItem("kz", "Kazakhstan"));
        countries.add(new SelectItem("ke", "Kenya"));
        countries.add(new SelectItem("ki", "Kiribati"));
        countries.add(new SelectItem("kw", "Kuwait"));
        countries.add(new SelectItem("kg", "Kyrgyzstan"));
        countries.add(new SelectItem("la", "Laos"));
        countries.add(new SelectItem("lv", "Latvia"));
        countries.add(new SelectItem("lb", "Lebanon"));
        countries.add(new SelectItem("ls", "Lesotho"));
        countries.add(new SelectItem("lr", "Liberia"));
        countries.add(new SelectItem("ly", "Libya"));
        countries.add(new SelectItem("li", "Liechtenstein"));
        countries.add(new SelectItem("lt", "Lithuania"));
        countries.add(new SelectItem("lu", "Luxembourg"));
        countries.add(new SelectItem("mo", "Macau"));
        countries.add(new SelectItem("mk", "Macedonia"));
        countries.add(new SelectItem("mg", "Madagascar"));
        countries.add(new SelectItem("mw", "Malawi"));
        countries.add(new SelectItem("my", "Malaysia"));
        countries.add(new SelectItem("mv", "Maldives"));
        countries.add(new SelectItem("ml", "Mali"));
        countries.add(new SelectItem("mt", "Malta"));
        countries.add(new SelectItem("mh", "Marshall Islands"));
        countries.add(new SelectItem("mq", "Martinique"));
        countries.add(new SelectItem("mr", "Mauritania"));
        countries.add(new SelectItem("mu", "Mauritius"));
        countries.add(new SelectItem("yt", "Mayotte"));
        countries.add(new SelectItem("mx", "Mexico"));
        countries.add(new SelectItem("md", "Moldova"));
        countries.add(new SelectItem("mc", "Monaco"));
        countries.add(new SelectItem("mn", "Mongolia"));
        countries.add(new SelectItem("ms", "Montserrat"));
        countries.add(new SelectItem("ma", "Morocco"));
        countries.add(new SelectItem("mz", "Mozambique"));
        countries.add(new SelectItem("mm", "Myanmar"));
        countries.add(new SelectItem("na", "Namibia"));
        countries.add(new SelectItem("nr", "Nauru"));
        countries.add(new SelectItem("np", "Nepal"));
        countries.add(new SelectItem("nl", "Netherlands"));
        countries.add(new SelectItem("an", "Netherlands Antilles"));
        countries.add(new SelectItem("nc", "New Caledonia"));
        countries.add(new SelectItem("nz", "New Zealand"));
        countries.add(new SelectItem("ni", "Nicaragua"));
        countries.add(new SelectItem("ne", "Niger"));
        countries.add(new SelectItem("ng", "Nigeria"));
        countries.add(new SelectItem("nu", "Niue"));
        countries.add(new SelectItem("nf", "Norfolk Island"));
        countries.add(new SelectItem("kp", "North Korea"));
        countries.add(new SelectItem("mp", "Northern Mariana Islands"));
        countries.add(new SelectItem("no", "Norway"));
        countries.add(new SelectItem("om", "Oman"));
        countries.add(new SelectItem("pk", "Pakistan"));
        countries.add(new SelectItem("pw", "Palau"));
        countries.add(new SelectItem("ps", "Palestinian Territories"));
        countries.add(new SelectItem("pa", "Panama"));
        countries.add(new SelectItem("pg", "Papua New Guinea"));
        countries.add(new SelectItem("py", "Paraguay"));
        countries.add(new SelectItem("pe", "Peru"));
        countries.add(new SelectItem("ph", "Philippines"));
        countries.add(new SelectItem("pn", "Pitcairn Islands"));
        countries.add(new SelectItem("pl", "Poland"));
        countries.add(new SelectItem("pt", "Portugal"));
        countries.add(new SelectItem("pr", "Puerto Rico"));
        countries.add(new SelectItem("qa", "Qatar"));
        countries.add(new SelectItem("re", "Reunion"));
        countries.add(new SelectItem("ro", "Romania"));
        countries.add(new SelectItem("ru", "Russia"));
        countries.add(new SelectItem("rw", "Rwanda"));
        countries.add(new SelectItem("sh", "Saint Helena and Dependencies"));
        countries.add(new SelectItem("kn", "Saint Kitts and Nevis"));
        countries.add(new SelectItem("lc", "Saint Lucia"));
        countries.add(new SelectItem("pm", "Saint Pierre and Miquelon"));
        countries.add(new SelectItem("vc", "Saint Vincent and the Grenadines"));
        countries.add(new SelectItem("ws", "Samoa"));
        countries.add(new SelectItem("sm", "San Marino"));
        countries.add(new SelectItem("st", "Sao Tome and Principe"));
        countries.add(new SelectItem("sa", "Saudi Arabia"));
        countries.add(new SelectItem("sn", "Senegal"));
        countries.add(new SelectItem("sc", "Seychelles"));
        countries.add(new SelectItem("sl", "Sierra Leone"));
        countries.add(new SelectItem("sg", "Singapore"));
        countries.add(new SelectItem("sk", "Slovakia"));
        countries.add(new SelectItem("si", "Slovenia"));
        countries.add(new SelectItem("sb", "Solomon Islands"));
        countries.add(new SelectItem("so", "Somalia"));
        countries.add(new SelectItem("za", "South Africa"));
        countries.add(new SelectItem("gs", "South Georgia and South Sandwich Islands"));
        countries.add(new SelectItem("kr", "South Korea"));
        countries.add(new SelectItem("es", "Spain"));
        countries.add(new SelectItem("pi", "Spratly Islands"));
        countries.add(new SelectItem("lk", "Sri Lanka"));
        countries.add(new SelectItem("sd", "Sudan"));
        countries.add(new SelectItem("sr", "Suriname"));
        countries.add(new SelectItem("sj", "Svalbard and Jan Mayen"));
        countries.add(new SelectItem("sz", "Swaziland"));
        countries.add(new SelectItem("se", "Sweden"));
        countries.add(new SelectItem("ch", "Switzerland"));
        countries.add(new SelectItem("sy", "Syria"));
        countries.add(new SelectItem("tw", "Taiwan"));
        countries.add(new SelectItem("tj", "Tajikistan"));
        countries.add(new SelectItem("tz", "Tanzania"));
        countries.add(new SelectItem("th", "Thailand"));
        countries.add(new SelectItem("tg", "Togo"));
        countries.add(new SelectItem("tk", "Tokelau"));
        countries.add(new SelectItem("to", "Tonga"));
        countries.add(new SelectItem("tt", "Trinidad and Tobago"));
        countries.add(new SelectItem("tn", "Tunisia"));
        countries.add(new SelectItem("tr", "Turkey"));
        countries.add(new SelectItem("tm", "Turkmenistan"));
        countries.add(new SelectItem("tc", "Turks And Caicos Islands"));
        countries.add(new SelectItem("tv", "Tuvalu"));
        countries.add(new SelectItem("ug", "Uganda"));
        countries.add(new SelectItem("ua", "Ukraine"));
        countries.add(new SelectItem("ae", "United Arab Emirates"));
        countries.add(new SelectItem("uk", "United Kingdom"));
        countries.add(new SelectItem("us", "United States"));
        countries.add(new SelectItem("um", "United States Minor Outlying Islands"));
        countries.add(new SelectItem("uy", "Uruguay"));
        countries.add(new SelectItem("vi", "US Virgin Islands"));
        countries.add(new SelectItem("uz", "Uzbekistan"));
        countries.add(new SelectItem("vu", "Vanuatu"));
        countries.add(new SelectItem("va", "Vatican City"));
        countries.add(new SelectItem("ve", "Venezuela"));
        countries.add(new SelectItem("vn", "Vietnam"));
        countries.add(new SelectItem("wf", "Wallis and Futuna"));
        countries.add(new SelectItem("eh", "Western Sahara"));
        countries.add(new SelectItem("ye", "Yemen"));
        countries.add(new SelectItem("zm", "Zambia"));
        countries.add(new SelectItem("zw", "Zimbabwe"));
        countries.add(new SelectItem("rs", "Serbia"));
        countries.add(new SelectItem("me", "Montenegro"));

        return countries;
    }

    public static List getSearchTypes() {
        ArrayList searchTypes = new ArrayList();

        searchTypes.add(new SelectItem("General", "General"));

        return searchTypes;
    }

    public static void sendErrorEmail(final String subject,
            final String message,
            final EntityManager em) {
        try {
            if ((Boolean) SystemOption.getOptionValueObject(em,
                    "developerEmailAlertActivated")) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Utils.postMail(null, null, null, subject, message,
                                    "text/plain", em);
                        } catch (Exception e) {
                            System.out.println("Error sending error mail: " + e);
                        }
                    }

                }.start();
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static ReturnMessage postMail(
            Session mailSession,
            Employee fromEmployee,
            JobManagerUser toUser,
            String subject,
            String message,
            String contentType,
            EntityManager em) {

        boolean debug = false;
        Message msg;

        try {
            // use default session if none was provided
            if (mailSession == null) {
                //Set the host smtp address
                Properties props = new Properties();
                props.put("mail.smtp.host", (String) SystemOption.getOptionValueObject(em, "mail.smtp.host"));

                // create some properties and get the default Session
                Session session = Session.getDefaultInstance(props, null);
                session.setDebug(debug);
                msg = new MimeMessage(session);
            } else {
                msg = new MimeMessage(mailSession);
            }

            // set the from and to address
            InternetAddress addressFrom = null;
            if (fromEmployee == null) {
                addressFrom = new InternetAddress(
                        (String) SystemOption.getOptionValueObject(em, "jobManagerEmailAddress"),
                        (String) SystemOption.getOptionValueObject(em, "jobManagerEmailName"));
            } else {
                addressFrom = new InternetAddress(
                        fromEmployee.getInternet().getEmail1(),
                        fromEmployee.getFirstName() + " " + fromEmployee.getLastName());
            }
            msg.setFrom(addressFrom);

            InternetAddress[] addressTo = new InternetAddress[1];
            if (toUser != null) {
                if (toUser.getEmployee().getInternet().getEmail1().isEmpty()) {
                    addressTo[0] = new InternetAddress(toUser.getUsername() + "@"
                            + ((List<String>) SystemOption.getOptionValueObject(em, "domainNames")).get(0));
                } else {
                    addressTo[0] = new InternetAddress(toUser.getEmployee().getInternet().getEmail1());
                }
            } else {
                // tk send message to developer. username and full name to be obtained from database in future.
                addressTo[0] = new InternetAddress(
                        (String) SystemOption.getOptionValueObject(em, "administratorEmailAddress"));
            }

            msg.setRecipients(Message.RecipientType.TO, addressTo);

            // Setting the Subject and Content Type
            msg.setSubject(subject);
            msg.setContent(message, contentType);
            Transport.send(msg);
            
            return new ReturnMessage();
            
        } catch (UnsupportedEncodingException | MessagingException e) {
            System.out.println("An error occurred while posting an email: " + e);
            return new ReturnMessage(false, "An error occurred while posting an email.");
        }

    }

}
