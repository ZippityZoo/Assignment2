package com.csc340.restapidemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author sunny
 */
@RestController
public class RestApiController {

    /**
     * Hello World API endpoint.
     *
     * @return response string.
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World";
    }

    /**
     * Greeting API endpoint.
     *
     * Writes a greeting in Json format.
     *
     * @return The json repsonse
     */
    @GetMapping("/greeting")
    public Object getGreeting() {

        // create `ObjectMapper` instance
        ObjectMapper mapper = new ObjectMapper();

        // create a JSON object
        ObjectNode greeting = mapper.createObjectNode();
        greeting.put("id", 1);
        greeting.put("sender", "user");
        greeting.put("recipient", "World");
        greeting.put("message", "Hello!");

        // print json
        System.out.println(greeting);
        return greeting;

    }

    /**
     * Intro API endpoint.
     *
     * @param name, the request parameter
     * @return The json response
     */
    @GetMapping("/intro")
    public String getIntro(@RequestParam(value = "name", defaultValue = "Dora") String name) {
        return String.format("Hola, soy %s!", name);
    }

    /**
     * Get a quote from quotable and it available at this endpoint.
     *
     * @return The quote json response
     */
    @GetMapping("/quote")
    public Object getQuote() {
        try {
            String url = "https://api.quotable.io/random";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jSonQuote = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jSonQuote);

            //Print the whole response to console.
            System.out.println(root);

            //Parse out the most important info from the response.
            String quoteAuthor = root.get("author").asText();
            String quoteContent = root.get("content").asText();
            System.out.println("Author: " + quoteAuthor);
            System.out.println("Quote: " + quoteContent);

            return root;

        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /quote";
        }
    }

    /**
     * Get a list of universities from hipolabs and make them available at this
     * endpoint.
     *
     * @return json array
     */
    @GetMapping("/univ")
    public Object getUniversities() {
        try {
            String url = "http://universities.hipolabs.com/search?name=sports";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jSonQuote = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jSonQuote);

            //Print the whole response to console.
            System.out.println(root);

            //Print relevant info to the console
            for (JsonNode rt : root) {
                String name = rt.get("name").asText();
                String country = rt.get("country").asText();
                System.out.println(name + ": " + country);
            }

            return root;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /univ";
        }
    }
    /**
     * gets a list of;
     * all the characters Super Smash Bros Roster.
     * The order they appear in the Smash Ultimate roster up to March 2020.
     * What Smash other games they are in.
     * Their home series.
     * @return 
     */
    @GetMapping("/smash")
    public Object getSmashMain(){
        try {
            String url = "https://smashbros-unofficial-api.vercel.app/api/v1/ultimate/characters";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper =new ObjectMapper();
            
            String jSonQuote = restTemplate.getForObject(url,String.class);
            JsonNode root = mapper.readTree(jSonQuote);
            
            System.out.println(root);
            
            List<String> appears = new ArrayList<>();
            List<String> series = new ArrayList<>();
            
            for(JsonNode rt: root){
                //names are stored in a array i used this to get them out
                for(JsonNode e: rt.get("alsoAppearsIn")){
                    appears.add(e.asText());
                }
                //ditto
                for(JsonNode e: rt.get("series")){
                    series.add(0, e.asText());
                }
                String name = rt.get("name").asText();
                String order = rt.get("order").asText();
                try{
                    int orderNum = Integer.parseInt(order);

                    if((orderNum%10 == 1)&&(orderNum<10||20<orderNum)){
                        System.out.print(name +"'s " +"appears "+ order+"st"+ " in the Ulitmate roster\n");
                    }else if((orderNum%10 == 2)&&(orderNum<10||20<orderNum)){
                        System.out.print(name +"'s " +"order is "+ order+"nd"+ " in the Ulitmate roster\n");
                    }else if((orderNum%10 == 3)&&(orderNum<10||20<orderNum)){
                        System.out.print(name +"'s " +"order is "+ order+"rd"+ " in the Ulitmate roster\n");
                    }else{
                        System.out.print(name +"'s " +"order is "+ order+"th"+ " in the Ulitmate roster\n");
                    }
                }catch(NumberFormatException e){
                    try{
                    int echoNum = Integer.parseInt(order.substring(0,order.length()-1));
                    if((echoNum%10 == 1)&&(echoNum<10||20<echoNum)){
                        System.out.println(name + " is an echo fighter");
                        System.out.print(name +"'s " +"appears also "+ echoNum+"st"+ " in the Ulitmate roster\n");
                    }else if((echoNum%10 == 2)&&(echoNum<10||20<echoNum)){
                        System.out.println(name + " is an echo fighter");
                        System.out.print(name +"'s " +"order is also "+ echoNum+"nd"+ " in the Ulitmate roster\n");
                    }else if((echoNum%10 == 3)&&(echoNum<10||20<echoNum)){
                        System.out.println(name + " is an echo fighter");
                        System.out.print(name +"'s " +"order is also "+ echoNum+"rd"+ " in the Ulitmate roster\n");
                    }else{
                        System.out.println(name + " is an echo fighter");
                        System.out.print(name +"'s " +"order is also "+ echoNum+"th"+ " in the Ulitmate roster\n");
                    }
                    }catch(NumberFormatException y){
                        System.out.print(name +"'s " + "order is 33rd to 35th in the Ultimate roster\n");
                        System.out.println("They consist of 3 characters Squirtle,Ivysaur,and Charizard");
                    }
                }
                System.out.print(name);
                if(!appears.isEmpty()){
                    System.out.print(" appears in the following Smash Bros games : ");
                    for(String en: appears){
                        System.out.print(en+",");
                    }
                }
                else
                    System.out.print(" appears only in Ultimate");
                
                System.out.print(" and is from the "+ series.get(0)+" series\n\n");
                appears.clear();
            }
            return root;
        }   catch(JsonProcessingException ex){
            Logger.getLogger(RestApiController.class.getName()).log(Level.SEVERE, null, ex);
            return "error in /smash";
        }
    }

}
