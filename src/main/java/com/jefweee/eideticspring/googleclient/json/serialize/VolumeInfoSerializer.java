package com.jefweee.eideticspring.googleclient.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jefweee.eideticspring.googleclient.json.VolumeInfo;

import java.io.IOException;

public class VolumeInfoSerializer extends StdSerializer<VolumeInfo> {

    public VolumeInfoSerializer(){
        this(null);
    }

    public VolumeInfoSerializer(Class<VolumeInfo> t){
        super(t);
    }

    @Override
    public void serialize(VolumeInfo value, JsonGenerator jsonGenerator, SerializerProvider provider) throws IOException {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("title", value.getTitle());
            jsonGenerator.writeObjectField("categories", value.getCategories());
            jsonGenerator.writeObjectField("authors", value.getAuthors());
            jsonGenerator.writeObjectField("description", value.getDescription());
            jsonGenerator.writeStringField("thumbnailLink", value.getImageLinks().getThumbnail());
            jsonGenerator.writeEndObject();
    }
}
