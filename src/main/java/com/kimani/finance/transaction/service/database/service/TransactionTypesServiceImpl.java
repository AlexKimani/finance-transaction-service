package com.kimani.finance.transaction.service.database.service;

import com.kimani.finance.transaction.service.api.models.request.TransactionTypesRequest;
import com.kimani.finance.transaction.service.api.models.response.TransactionTypesResponse;
import com.kimani.finance.transaction.service.database.models.TransactionTypes;
import com.kimani.finance.transaction.service.database.repositories.TransactionTypesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class TransactionTypesServiceImpl {
    private final TransactionTypesRepository transactionTypesRepository;

    public Flux<TransactionTypes> findAllTransactionTypes() {
        return transactionTypesRepository.findAll(Sort.by(Sort.DEFAULT_DIRECTION,"id"));
    }

    public Mono<TransactionTypesResponse> getTransactionTypesById(long id) {
        return transactionTypesRepository.findTransactionTypesByIdEquals(id)
                .flatMap(this::setTransactionTypesResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(error -> {
                    log.error("Error occurred get transaction type by id: ", error);
                    return Mono.empty();
                });
    }

    public Mono<TransactionTypesResponse> getTransactionTypesByTransactionType(String transactionType) {
        return transactionTypesRepository.findTransactionTypesByTransactionTypeEqualsIgnoreCase(transactionType)
                .flatMap(this::setTransactionTypesResponse)
                .switchIfEmpty(Mono.empty())
                .onErrorResume(error -> {
                    log.error("Error occurred get transaction type by type: ", error);
                    return Mono.empty();
                });
    }

    public Mono<TransactionTypesResponse> updateTransactionType(long id, TransactionTypesRequest request) {
        log.info("Updating TransactionType: ID: {} Data {}", id, request.toString());
        return transactionTypesRepository.findTransactionTypesByIdEquals(id)
                .flatMap(transactionTypes -> {
                    TransactionTypes data = TransactionTypes.builder()
                            .transactionType(request.getTransactionType())
                            .transactionTax(request.getTransactionTax())
                            .transactionFee(request.getTransactionFee())
                            .build();
                    data.setId(id);
                    data.setCreatedAt(transactionTypes.getCreatedAt());
                    return transactionTypesRepository.save(data)
                            .flatMap(this::setTransactionTypesResponse)
                            .onErrorResume(error -> {
                                log.error("Error occurred updating transaction type: ", error);
                                return Mono.empty();
                            });
                }).switchIfEmpty(createNewTransactionType(request))
                .onErrorResume(error -> {
                    log.error("Error occurred updating transaction type: ", error);
                    return Mono.empty();
                });
    }

    public Mono<TransactionTypesResponse> createTransactionType(TransactionTypesRequest request) {
        return transactionTypesRepository.findTransactionTypesByTransactionTypeEqualsIgnoreCase(request.getTransactionType())
                .flatMap(this::setTransactionTypesResponse)
                .switchIfEmpty(createNewTransactionType(request))
                .onErrorResume(error -> {
                    log.error("Error occurred creating transaction type: ", error);
                    return Mono.empty();
                });
    }

    private Mono<TransactionTypesResponse> createNewTransactionType(TransactionTypesRequest request) {
        log.info("Creating new TransactionType: {}", request.toString());
        TransactionTypes transactionTypes = TransactionTypes.builder()
                .transactionType(request.getTransactionType())
                .transactionFee(request.getTransactionFee())
                .transactionTax(request.getTransactionTax())
                .build();
        return transactionTypesRepository.save(transactionTypes)
                .flatMap(this::setTransactionTypesResponse)
                .onErrorResume(error -> {
                    log.error("Error occurred creating transaction type: ", error);
                    return Mono.empty();
                });
    }

    private Mono<TransactionTypesResponse> setTransactionTypesResponse(TransactionTypes transactionTypes) {
        TransactionTypesResponse response = TransactionTypesResponse.builder()
                .id(transactionTypes.getId())
                .transactionType(transactionTypes.getTransactionType())
                .transactionFee(transactionTypes.getTransactionFee())
                .transactionTax(transactionTypes.getTransactionTax())
                .build();
        return Mono.justOrEmpty(response);
    }
}
