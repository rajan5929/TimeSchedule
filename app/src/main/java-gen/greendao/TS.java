package greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table _table_common.
 */
public class TS {

    private Long id;
    private Boolean b_finish;
    private String s_title;
    private Integer i_status;

    public TS() {
    }

    public TS(Long id) {
        this.id = id;
    }

    public TS(Long id, Boolean b_finish, String s_title, Integer i_status) {
        this.id = id;
        this.b_finish = b_finish;
        this.s_title = s_title;
        this.i_status = i_status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getB_finish() {
        return b_finish;
    }

    public void setB_finish(Boolean b_finish) {
        this.b_finish = b_finish;
    }

    public String getS_title() {
        return s_title;
    }

    public void setS_title(String s_title) {
        this.s_title = s_title;
    }

    public Integer getI_status() {
        return i_status;
    }

    public void setI_status(Integer i_status) {
        this.i_status = i_status;
    }

}
