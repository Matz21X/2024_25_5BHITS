package at.htlhl.graphdemo;

public class VertexData {

    private String name;

    public VertexData(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
