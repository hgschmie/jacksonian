package ness.jacksonian;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.common.base.Preconditions;

public class Jacksonian
{
    public static void main(String[] args) throws Exception
    {
        final JsonFactory jf = new JsonFactory();
        final ObjectMapper mapper = new ObjectMapper();
        final JsonParser jp;

        if(args.length > 0) {
            jp = jf.createJsonParser(new File(args[0]));
        }
        else {
            jp = jf.createJsonParser(System.in);
        }

        final Writer wr;
        if (args.length > 1) {
            wr = new FileWriter(new File(args[1]));
        }
        else {
            wr = new OutputStreamWriter(System.out);
        }

        Preconditions.checkState(jp.nextToken() == JsonToken.START_ARRAY);
        while (jp.nextToken() != JsonToken.END_ARRAY) {
            final JsonNode jn = mapper.readTree(jp);

            final JsonGenerator jg = jf.createJsonGenerator(wr);
            mapper.writeTree(jg, jn);

            wr.append("\n");
        }
    }
}