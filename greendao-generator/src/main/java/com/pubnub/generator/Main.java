package com.pubnub.generator;



import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class Main {

    public static  void main(String[] args) throws Exception {
        Schema schema = new Schema(1,"com.woofyapp.pubnub.database");

        Entity group = schema.addEntity("Group");
        group.addStringProperty("groupId").primaryKey();
        group.addStringProperty("groupName");

        Entity chat = schema.addEntity("chat");
        chat.addIdProperty();
        chat.addStringProperty("message");
        chat.addStringProperty("from");
        Property grpId = chat.addStringProperty("groupId").getProperty();
        chat.addToOne(group,grpId);

        ToMany chatTOgroup = group.addToMany(chat,grpId);
        chatTOgroup.setName("chats");

        DaoGenerator dao = new DaoGenerator();
        dao.generateAll(schema,"./app/src/main/java");
    }
}

