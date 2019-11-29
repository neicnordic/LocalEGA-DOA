package no.uio.ifi.localega.doa.repositories;

import no.uio.ifi.localega.doa.model.LEGAFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<LEGAFile, BigInteger> {

    Optional<LEGAFile> findByFileId(String fileId);

}