package pilnik.belstu.lab3;

public class Notes {
    private String text;
    private String date;

    Notes(String text,String date){
        this.text = text;
        this.date = date;
    }

    public String getText(){
        return text;
    }

    public String getDate(){
        return date;
    }

    public void setText(String text){
        this.text=text;
    }

    public void setDate(String date){
        this.date = date;
    }
}
