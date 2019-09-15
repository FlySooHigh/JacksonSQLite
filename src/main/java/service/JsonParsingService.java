package service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.google.common.collect.Lists;
import config.Config;
import lombok.Value;
import model.Colour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repository.impl.ColourJdbcRepoImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParsingService {

    @Autowired
    private ColourJdbcRepoImpl colourJdbcRepo;

    private final String PATH_TO_JSON = "D:\\JAVA\\PROJECTS\\JacksonSQLite\\src\\main\\resources\\colors.json";

    public static void main(String[] args) throws IOException {
        new JsonParsingService().start();
    }

    private List<Colour> start() throws IOException {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class);
        colourJdbcRepo = (ColourJdbcRepoImpl) ctx.getBean("repository.impl.ColourJdbcRepoImpl");

        ArrayList<Colour> colours = new ArrayList<>();

        System.out.println("Start parsing and inserting into DB right away...");
        long start = System.currentTimeMillis();

        JsonParser parser = new JsonFactory().createParser(new File(PATH_TO_JSON));
        parser.nextToken(); // JsonToken.START_OBJECT;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            Colour colour = readColour(parser);
            colourJdbcRepo.insert(colour);
            System.out.println(colour);
            colours.add(colour);
        }
        parser.close();
        long stop = System.currentTimeMillis();
        System.out.println("Time to insert into DB: " + (stop - start) / 1000 + " sec");


        System.out.println("Number of objects in colours collection: " + colours.size());
        SQLiteService sqLiteService = new SQLiteService();
//        sqLiteService.createNewTable();

//        Colour insert = colourJdbcRepo.insert(colours.get(1));

        int partitionSize = 100;
        List<List<Colour>> partitions = new ArrayList<>();



        System.out.println("Starting insert into DB...");
        long start1 = System.currentTimeMillis();
//        for (int i = 0; i < colours.size(); i += partitionSize) {
//            colourJdbcRepo.insertBatch(colours.subList(i, Math.min(i + partitionSize, colours.size())));
//        }

        // FIXME: 15.09.2019 Use Guava!
//        for (List<Colour> partition : Lists.partition(colours, 50)) {
//            colourJdbcRepo.insertBatch(partition);
//        }
        long stop1 = System.currentTimeMillis();
        System.out.println("Time to insert batch into DB: " + (stop1 - start1) / 1000 + " sec");

//        List<Colour> insertBatch = colourJdbcRepo.insertBatch(colours.subList(0,5));

        return colours;
    }

    private Colour readColour(JsonParser parser) throws IOException {
        final String name = parser.getCurrentName();
        parser.nextToken(); // JsonToken.START_ARRAY;
        final Colour colour = new Colour(
                null,
                name,
                readInt(parser),
                readInt(parser),
                readInt(parser),
                readInt(parser)
        );
        parser.nextToken(); // JsonToken.END_ARRAY;
        return colour;
    }

    private int readInt(JsonParser parser) throws IOException {
        parser.nextValue();
        return parser.getIntValue();
    }

//    @Value
//    class Colour {
//        private Long id;
//        private String name;
//        private int red;
//        private int green;
//        private int blue;
//        private int alpha;
//    }
}
