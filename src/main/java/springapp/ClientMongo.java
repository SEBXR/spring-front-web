package springapp;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ClientMongo")
public class ClientMongo {
    String _id;
    Integer idClient ;
    String emailclients;
    String mdpclients;
    public ClientMongo() {}

    public ClientMongo(String emailclients, String mdpclients) {
        this.emailclients = emailclients;
        this.mdpclients = mdpclients;
    }

    public ClientMongo(Integer id, String emailclients, String mdpclients) {
        this.idClient = id;
        this.emailclients = emailclients;
        this.mdpclients = mdpclients;
    }

    public ClientMongo(String _id, Integer id, String emailclients, String mdpclients) {
        this._id = _id;
        this.idClient = id;
        this.emailclients = emailclients;
        this.mdpclients = mdpclients;
    }


    public String get_id() {
        return this._id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getId() {
        return idClient;
    }
    
    public void setId(Integer id){
        this.idClient = id;
    }

    public String getEmailclients() {
        return emailclients;
    }

    public void setEmailclients(String emailclients) {
        this.emailclients = emailclients;
    }
   

    public String getMdpclients() {
        return mdpclients;
    }

    public void setMdpclients(String mdpclients) {
        this.mdpclients = mdpclients;
    }

    @Override
    public String toString() {
        return "ClientMongo [_id=" + _id + ", idClient=" + idClient + ", emailclients=" + emailclients + ", mdpclients="
                + mdpclients + "]";
    }

    
    
}
