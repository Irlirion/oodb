package com.company.lab3;

import com.company.lab3.domain.SocialNetwork;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class Lab3XMLService {
    /**
     * метод saveSocialNetworkData сохраняет объект типа Bank в XML документ
     */
    public static void saveSocialNetworkData(SocialNetwork socialNetwork) {

        try {
            JAXBContext context = JAXBContext.newInstance(SocialNetwork.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(socialNetwork, new File("socialNetwork.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод loadSocialNetworkFromXML преобразует XML документ в объект типа Bank
     */
    public static SocialNetwork loadSocialNetworkFromXML() {

        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(SocialNetwork.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (SocialNetwork) un.unmarshal(new File("socialNetwork.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
