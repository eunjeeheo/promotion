package phoneseller;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Promotion_table")
public class Promotion {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id; // 프로모션ID
    private Long orderId; // 주문ID
    private Double point; // 페이백 포인트
    private String process; // 상태

    @PostPersist
    public void onPostPersist(){

        if("Payed".equals(process) && point > 0){
            System.out.println("*** 결제 완료 -> 포인트 적립 ***");

            // 결제 완료된 이벤트리스너를 통해 페이백 포인트 제공
            PromoCompleted promoCompleted = new PromoCompleted();
            BeanUtils.copyProperties(this, promoCompleted);
            promoCompleted.publish();

            System.out.println("*** 프로모션 - 페이백 포인트 제공 완료 ***");


        } else if("PayCancelled".equals(process)){
            PromoCancelled promoCancelled = new PromoCancelled();
            BeanUtils.copyProperties(this, promoCancelled);
            promoCancelled.publish();
            System.out.println("*** 결제 취소로 인한 프로모션 포인트 제공 회수 ***");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", point=" + point +
                ", process=" + process +
                '}';
    }
}
