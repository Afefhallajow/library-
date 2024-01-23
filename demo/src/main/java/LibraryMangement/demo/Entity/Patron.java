package LibraryMangement.demo.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"borrowingRecords"})
public class Patron {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name must not be blank")
    private String name;

    @NotBlank(message = "contactInformation must not be blank")
    private String contactInformation;
    @JsonBackReference
    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<BorrowingRecord> borrowingRecords =new HashSet<>();

    public Patron(Object id, String name, String contactInformation) {
this.id= (Long) id;
        this.name=name;
    this.contactInformation=contactInformation;
    }

    public Patron() {
    }

    public Set<BorrowingRecord> getBorrowingRecords() {
        return borrowingRecords;
    }

    public void setBorrowingRecords(Set<BorrowingRecord> borrowingRecords) {
        this.borrowingRecords = borrowingRecords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}