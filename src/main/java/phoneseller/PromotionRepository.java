package phoneseller;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface PromotionRepository extends PagingAndSortingRepository<Promotion, Long>{

    Optional<Promotion> findByOrderId(Long orderId);

}





