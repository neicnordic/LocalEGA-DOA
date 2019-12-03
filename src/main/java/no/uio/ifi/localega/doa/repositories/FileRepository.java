package no.uio.ifi.localega.doa.repositories;

import no.uio.ifi.localega.doa.model.LEGAFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<LEGAFile, String> {
}
