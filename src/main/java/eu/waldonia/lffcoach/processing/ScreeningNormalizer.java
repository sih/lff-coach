package eu.waldonia.lffcoach.processing;

import eu.waldonia.lffcoach.model.Screening;

import java.util.List;

/**
 * @author sih
 */
abstract class ScreeningNormalizer {

    abstract void normalize(List<Screening> screenings);
}
