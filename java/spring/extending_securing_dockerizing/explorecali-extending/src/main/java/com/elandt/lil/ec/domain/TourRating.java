package com.elandt.lil.ec.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tour_rating")
public class TourRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String comment;

    protected TourRating() {
        // Default constructor - protected to force use of contructor below
    }

    public TourRating(Tour tour, Integer customerId, Integer score, String comment) {
        this.tour = tour;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }

    public TourRating(Tour tour, Integer customerId, Integer score) {
        this.tour = tour;
        this.customerId = customerId;
        this.score = score;
        this.comment = toComment(score);
    }

    /**
     * Auto generate a comment for the given score
     *
     * @param score Integer score (1-5)
     * @return auto-generated comment
     */
    private String toComment(Integer score) {
        switch (score) {
            case 1:
                return "Terrible";
            case 2:
                return "Poor";
            case 3:
                return "Fair";
            case 4:
                return "Good";
            case 5:
                return "Great";
            default:
                return score.toString();
        }
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((tour == null) ? 0 : tour.hashCode());
        result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
        result = prime * result + ((score == null) ? 0 : score.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        TourRating other = (TourRating) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (tour == null) {
            if (other.tour != null) {
                return false;
            }
        } else if (!tour.equals(other.tour)) {
            return false;
        }
        if (customerId == null) {
            if (other.customerId != null) {
                return false;
            }
        } else if (!customerId.equals(other.customerId)) {
            return false;
        }
        if (score == null) {
            if (other.score != null) {
                return false;
            }
        } else if (!score.equals(other.score)) {
            return false;
        }
        if (comment == null) {
            if (other.comment != null) {
                return false;
            }
        } else if (!comment.equals(other.comment)) {
            return false;
        }
        return true;
    }
}
